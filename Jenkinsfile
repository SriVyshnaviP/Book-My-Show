pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'srivyshnavip/bms:latest'
        AWS_REGION = 'ap-south-1'
        EKS_CLUSTER_NAME = 'vishnus-eks'
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout from Git') {
            steps {
                git branch: 'main', url: 'https://github.com/SriVyshnaviP/Book-My-Show.git'
                sh 'ls -la'
            }
        }
        stage('Install Dependencies') {
            steps {
                sh '''
                cd bookmyshow-app
                ls -la  # Verify package.json exists
                if [ -f package.json ]; then
                    rm -rf node_modules package-lock.json  # Remove old dependencies
                    npm install  # Install fresh dependencies
                else
                    echo "Error: package.json not found in bookmyshow-app!"
                    exit 1
                fi
                '''
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'docker') {
                        sh ''' 
                        echo "Building Docker image..."
                        docker build --no-cache -t $DOCKER_IMAGE -f bookmyshow-app/Dockerfile bookmyshow-app

                        echo "Pushing Docker image to Docker Hub..."
                        docker push $DOCKER_IMAGE
                        '''
                    }
                }
            }
        }

        stage('Deploy to EKS') {
            steps {
                sh '''
                ansible-playbook deploy-eks.yml
                '''
            }
        }
    }
}
