pipeline {
    agent any
    tools { 
        maven 'maven 3.8'  
    }
    stages {
        stage ('Git checkout') { 
          steps {
            git branch: 'riadh', url: 'https://github.com/CodeWizards007/devops-API.git'
          }
          
        }
    

        stage ('Test') {
            steps {
                sh 'mvn test'
              
            }
        }
         stage ('Build') {
            steps {
           
                sh 'mvn clean install'
            }
        }
    }
}
