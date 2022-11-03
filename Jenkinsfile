@Library('jenkins-shared-library')_


pipeline {
    agent any
    tools{
    maven "maven"
    }
   
    stages {
        stage("Email notification")
        {
        steps{
            echo "${BUILD_URL}"
            mail bcc: '', body: "Check console output at ${env.BUILD_URL}consoleText to view the results.", cc: '', from: '', replyTo: '', subject: "${env.BRANCH_NAME} - Build # ${env.BUILD_TAG}", to: 'hamdi.nahdi@esprit.tn,saifeddine.houji@esprit.tn,riadh.yahyaoui@esprit.tn,tarek.zaafrane@esprit.tn ,teymour.dridi@esprit.tn '
           
        }
    }
}   

