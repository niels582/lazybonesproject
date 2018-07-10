#!groovy
node {
    wrap([$class: 'AnsiColorBuildWrapper', colorMapName: "xterm"]) {
        def gradle = tool 'gradle'
        def imagepush= 'aws-project-springboot'
        def awsid='085763616923'
        def awsregion='us-east-1'
        def imagepushaws="${awsid}.dkr.ecr.${awsregion}.amazonaws.com/${imagepush}"
        try {
            stage('Checkout') {
                properties([pipelineTriggers([[$class: 'GitHubPushTrigger'], pollSCM('* * * * *')])])
                checkout scm
            }

            stage('Build') {
                sh "${gradle}/bin/gradle docker -x test"
            }
            stage("Push ECR"){
//                sh "usuario: ${env.HOSTNAME}"
                sh "sed -i -- 's|${imagepush}|${imagepushaws}|g' base.yml"
                sh "eval \$(aws ecr get-login --no-include-email | sed 's|https://||')"
                sh "docker-compose build && docker-compose push"
//                sh """docker tag ${imagepush}:latest ${imagepushaws}:latest
//                      docker push ${imagepushaws}:latest
//                   """
            }

        } catch (err) {

            throw err
        }

    }
}