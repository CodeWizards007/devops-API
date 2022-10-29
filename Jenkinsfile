@Library('jenkins-shared-library')_

pipeline {
    agent any
    tools{
    maven "maven"
    }
    stages {
        stage("sonarqube analysis")
        {
           steps{
             script{
                withSonarQubeEnv(credentialsId: 'jenkins-auth')
                {
                    sh 'mvn clean package sonar:sonar'
                }
             }
           }
        }
        stage("Quality status")
        {
           steps{
             script{
                waitForQualityGate abortPipeline: false, credentialsId: 'jenkins-auth'
             }
           }
        }
        

        stage("build poject")
        {
            steps{
                sh "mvn clean package"
            }
        }
        stage("upload artifact on nexus")
        {
            steps{
                script{

                    nexusArtifactUploader artifacts: 
                    [[artifactId: 'achat', classifier: '', 
                    file: 'target/achat-1.0.jar', type: 'jar']], 
                    credentialsId: 'jenkins-nexus-auth', groupId: 'tn.esprit.rh', 
                    nexusUrl: '127.0.0.1:8081', 
                    nexusVersion: 'nexus2', 
                    protocol: 'http',
                    repository: 'maven-app', 
                    version: '1.0'

                }
            }
        }
    }
}
