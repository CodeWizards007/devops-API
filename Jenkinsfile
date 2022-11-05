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

                
                sh "mvn test"

                
            }
        }
        stage('Build'){
            steps {
              
                // Maven build .
                sh "mvn clean install -Dmaven.test.skip=true"
                
            }
        }
        
        stage("sonarqube analysis")
        {
           steps{
             script{
                withSonarQubeEnv(credentialsId: 'sonar-api')
                {
                    sh 'mvn clean package sonar:sonar'
                }
             }
           }
        }
        
        stage("quality gate")
        {
           steps{
             script{
                 
               waitForQualityGate abortPipeline: false, credentialsId: 'sonar-api'
              
             }
           }
        }
        
        stage("publish the jar to nexus repo")
        {
           steps{
             script{
                 nexusArtifactUploader artifacts: [
                     [artifactId: 'achat',
                      classifier: '',
                      file: 'target/achat.jar',
                      type: 'jar']],
                     credentialsId: 'nexus-auth',
                     groupId: 'tn.esprit.rh',
                     nexusUrl: '192.168.1.34:8081',
                     nexusVersion: 'nexus3',
                     protocol: 'http',
                     repository: 'SpringAppRelease',
                     version: '1.0'
              
             }
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
      
