pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test -f pom.xml'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
//                        sh "${tool('sonar-scanner')}/bin/sonar-scanner -Dsonar.projectKey=${sonar_project} -Dsonar.projectName=${sonar_project}"
                        sh 'mvn sonar:sonar -Pcoverage'
                    }
                }
            }
        }
//        stage("Quality Gate") {
//            steps {
//              timeout(time: 1, unit: 'HOURS') {
//                waitForQualityGate abortPipeline: true
//              }
//            }
//          }
        stage('Deploy') {
            steps {
                sh './jenkins/scripts/deploy.sh'
            }
        }
    }
}