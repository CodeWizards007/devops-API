pipeline {
    agent any
    tools{
    maven "maven" 
    }

    
    environment {
        DOCKERHUB_USERNAME ="eyahadrich"
        DOCKERHUB_REPO = "devops"
        TARGET_BRANCH = "master" 
    } 

    stages {
        /*
        stage("increment project version")
        {
            steps{
                script{
                sh 'mvn -Dmaven.test.skip=true build-helper:parse-version versions:set\
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                    versions:commit'
                def matcher =  readFile('pom.xml')=~'<version>(.+)</version>'
                def version = matcher[1][1]
                echo "${version}"
                env.IMAGE_NAME= "$version"
            }          
            }
        }
         stage("commit version update")
        {
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'github-auth', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh 'pwd'
                        sh 'git config --global user.email "jenkins@exemple.com"'
                        sh 'git config --global user.name "jenkins"'
                        sh 'git config --list'
                        sh 'git remote set-url origin  https://${USERNAME}:${PASSWORD}@github.com/eyahadrich/tpAchatProject'
                        sh 'git add .'
                        sh 'git commit -m "update project version"'
                        sh 'git pull origin ${TARGET_BRANCH}'
                        sh 'git branch'
                        sh 'git push origin HEAD:${TARGET_BRANCH}'
                    }
                }
            }
        }
         stage("build poject")
        {
            steps{
                echo 'building maven project'
                sh 'mvn -Dmaven.test.skip=true clean package'
            }
        }
        stage('Unit test')
        {
            steps{
                echo " testing the app .."
                sh "mvn test"
            }
        }*/
        stage("sonarqube analysis")
        {
             
           steps{
             script{
                withSonarQubeEnv(installationName: 'sonarqube')
                {
                    sh 'mvn clean sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=eya97'
                }
                
             }
           }
        }

        stage("Quality status")
        { 
           steps{
             script{
                waitForQualityGate abortPipeline: false, credentialsId: 'sonarqube-token'
             }
           }
        }
         stage("build docker image")
        {
            
            steps{
                echo "building docker images"
                sh "docker image prune"
                sh "docker container prune"
                sh "docker build -t ${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:maven-${IMAGE_NAME} ."
            }
        }
        stage("pushing docker image to dockerhub")
        {
              
         steps{
         echo "pushing docker images ... "
            withCredentials([usernamePassword(credentialsId: 'dockerhub_cred', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
               echo "login to dockerhub images repos"
               sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
               echo "push the images to dockerhub"
               sh "docker push ${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:maven-${IMAGE_NAME}"
            }    
        }           
        }
        stage("run app with docker-compose")
        {
            steps{
                sh "docker-compose down"
                sh "IMAGE_NAME=${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:maven-${IMAGE_NAME} docker-compose up -d --no-recreate" 
            }
        }
        stage("Email notification")
        {
        steps{
            echo "${BUILD_URL}"
            mail bcc: '', body: "Check console output at ${env.BUILD_URL}consoleText to view the results.", cc: '', from: '', replyTo: '', subject: "${env.BRANCH_NAME} - Build # ${env.BUILD_TAG}", to: 'hamdi.nahdi@esprit.tn'
           
        }
        }
    }
}
