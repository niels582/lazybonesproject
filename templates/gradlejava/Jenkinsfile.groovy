#!groovy
node {
    
        def gradle = tool 'gradle'
        def imagepush= '${projectName}'
        def awsid='${awsId}'
        def awsregion='${awsRegion}'
        def imagepushaws="&#awsid#&.dkr.ecr.&#awsregion#&.amazonaws.com/&#imagepush#&"
        try {
            stage('Checkout') {
                properties([pipelineTriggers([&1class: 'GitHubPushTrigger'], pollSCM('* * * * *')])])
                checkout scm
            }

            stage('Build') {
                sh "docker rmi -f &#imagepush#&:latest &#imagepushaws#&:latest || true"
                sh "&#gradle#&/bin/gradle docker -x test"
            }
            stage("Push ECR"){
                sh "sed -i -- 's|&#imagepush#&|&#imagepushaws#&|g' base.yml"
                docker.withRegistry("https://&#awsid#&.dkr.ecr.&#awsregion#&.amazonaws.com", "ecr:&#awsregion#&:aws") {
                    docker.image("&#imagepush#&").push('latest')
                }
//                sh "docker-compose push"
//                sh """docker tag &#imagepush#&:latest &#imagepushaws#&:latest
//                      docker push &#imagepushaws#&:latest
//                   """
            }

        } catch (err) {

            throw err
        }

    //}
}