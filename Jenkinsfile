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
                def version = matcher[0][1]
                env.IMAGE_NAME= "$version"
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
    }
}
