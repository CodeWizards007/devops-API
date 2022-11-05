pipeline {
    agent any

    tools {
        maven "maven-3.8"
    }

    stages {
        stage('Test') {
            steps {
                // Get some code from a GitHub repository
               git branch: 'riadh', url: 'https://github.com/CodeWizards007/devops-API.git'


                
            }
        }
        stage('Build'){
            steps {
              
                // Maven build .
                sh "mvn clean install -Dmaven.test.skip=true"
                
            }
        }
        
        stage('Build docker image '){
            steps {

                echo "Build a docker image"
                sh "docker build -t spring . "
                sh "docker images"

                
            }
        }

        stage('Deploy on docker compose '){
            steps {

                echo "Deploying"
              
                sh "docker-compose up -d"
        
                
             
            }
        }
          
    }
}
      
