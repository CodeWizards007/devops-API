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
                        sh 'git pull origin hamdi'
                        sh 'git branch'
                        sh 'git push origin HEAD:hamdi'
                    }
                }
            }
        }
        stage("sonarqube analysis")
        {
            when{
             expression{
             branch "master"
             }
            }
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
            when{
             expression{
             branch "master"
             }
            }
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
             when{
             expression{
             branch "master"
             }
             }
            steps{
               echo "building docker images"
                buildImage("${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:maven-${IMAGE_NAME}")
            }
        }
        stage("pushing docker image to dockerhub")
        {
             when{
             expression{
             branch "master"
             }
             }
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
         when{
             expression{
             branch "master"
             }
            }
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
            script{
                emailext body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS: 
                Check console output at $BUILD_URL to view the results.''', recipientProviders: [developers("hamdinahdi2@gmail.com")], subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!'
            }
        }
    }
    }

    
}
