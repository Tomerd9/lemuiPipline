pipeline {
    agent none
    stages {
        stage('Build app with Docker') {
            agent {
                label 'serverOne' // Label of server 1 for Docker build
            }
            steps {
                script {
                    checkout scm
// Building and running the app using docker, after the run instead the slpeep you can enter command/script to run test on the app
                    sh '''
                    docker build -t python_test_app .
                    docker push tomerdar/test_app:python_test_app
                    docker run -d --name python_test_app_container -p 8080:8080 tomerdar/test_app:python_test_app
                    sleep 10
                    docker stop python_test_app_container
                    docker rm python_test_app_container
 
                    '''
                }
            }
        }

        stage('Deploy app with Kubernetes') {
            agent {
                label 'serverTwo' // Label of server 2 for Kubernetes deployment
            }
            steps {
                script {
                    checkout scm
// Applying the Kubernetes configurations and deploying the app using kubernetes and wxposing port 443 to the internet
                    sh '''
                    kubectl --kubeconfig=$HOME/.kube/config apply -f deployment.yaml
                    kubectl --kubeconfig=$HOME/.kube/config apply -f service.yaml
                    '''
                }
            }
        }
    }
}
