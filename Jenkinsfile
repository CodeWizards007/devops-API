@Library('jenkins-shared-library')_
pipeline {
    agent any
    tools{
    maven "maven"
        
    }
    environment {
        // This can be nexus3 or nexus2
        NEXUS_VERSION = "nexus2"
        // This can be http or https
        NEXUS_PROTOCOL = "http"
        // Where your Nexus is running
        NEXUS_URL = "20.150.204.104/nexus"
        // Repository where we will upload the artifact
        NEXUS_REPOSITORY = "maven-app"
        // Jenkins credential id to authenticate to Nexus OSS
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials" // 3malt credentials f jenkins w 3aythomlhom houni for security reasons
        DOCKERHUB_USERNAME ="hamdinh98"
        DOCKERHUB_REPO = "images-repo"
        TARGET_BRANCH = "hamdi" // hedi tetbadel selon el branch eli bech truni aleha script
    } 
    stages {
        stage("Increment version")
        {
            steps{
                script{
                sh 'mvn build-helper:parse-version versions:set\
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
                    withCredentials([usernamePassword(credentialsId: 'jenkins-github-auth', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh 'git config --global user.email "jenkins@exemple.com"'
                        sh 'git config --global user.name "jenkins"'
                        sh 'git config --list'
                        sh 'git remote set-url origin  https://${USERNAME}:${PASSWORD}@github.com/CodeWizards007/devops-API'
                        sh 'git add .'
                        sh 'git commit -m "update project version"'
                        sh 'git pull origin ${TARGET_BRANCH}'
                        sh 'git branch'
                        sh 'git push origin HEAD:${TARGET_BRANCH}'
                    }
                }
            }
        }
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
                echo 'building maven project'
                buildJar()
            }
        }
        stage('Unit test')
        {
            steps{
                echo " testing the app .."
                sh "mvn test"
            }
        }

        stage("build docker image")
        {
            
            steps{
             echo "building docker images"
                buildImage("${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:maven-${IMAGE_NAME}")
            }
            
              
        }
        stage("pushing docker image to dockerhub")
        {
              
         steps{
         echo "pushing docker images ... "
            withCredentials([usernamePassword(credentialsId: 'docker-hub-login', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
               echo "login to dockerhub images repos"
               sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
               echo "push the images to dockerhub"
               sh "docker push ${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:maven-${IMAGE_NAME}"
              }    
        }           
        }
        stage("Publish to Nexus") {
              
            steps {
                script {
                    // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                    def pom = readMavenPom file: 'pom.xml';
                    writeMavenPom model: pom;
                    // Find built artifact under target folder
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    // Print some info from the artifact found
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    // Extract the path from the File found
                    artifactPath = filesByGlob[0].path;
                    // Assign to a boolean response verifying If the artifact name exists
                    artifactExists = fileExists artifactPath;

                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";

                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                // Artifact generated such as .jar, .ear and .war files.
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],

                                // Lets upload the pom.xml file for additional information for Transitive dependencies
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );

                    } else {
                        error "*** File: \${artifactPath}, could not be found";
                    }
                }
            }
        }
        stage("Email notification")
        {
        steps{
            echo "${BUILD_URL}"
            mail bcc: '', body: "Check console output at ${env.BUILD_URL}consoleText to view the results.", cc: '', from: '', replyTo: '', subject: "${env.BRANCH_NAME} - Build # ${env.BUILD_TAG}", to: 'hamdi.nahdi@esprit.tn,saifeddine.houji@esprit.tn,riadh.yahyaoui@esprit.tn,tarek.zaafrane@esprit.tn ,teymour.dridi@esprit.tn '
           
        }
        }
    }
}





