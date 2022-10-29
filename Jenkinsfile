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
    }
}
