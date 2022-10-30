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
                withSonarQubeEnv(credentialsId: 'sonar_auth')
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
                 
               waitForQualityGate abortPipeline: false, credentialsId: 'sonar_auth'
              
             }
           }
        }
          
    }
}
      
