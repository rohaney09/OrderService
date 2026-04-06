// pipeline {
//     agent any
//
//     tools {
//         maven 'Maven3'   // make sure Maven is configured in Jenkins
//         jdk 'JDK17'      // match your Java version
//     }
//
//     stages {
//
//         stage('Clone') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/rohaney09/OrderService.git'
//             }
//         }
//
//         stage('Build') {
//             steps {
//                 bat 'mvn clean install -DskipTests'
//             }
//         }
//
//         stage('Test') {
//             steps {
//                 bat 'mvn test'
//             }
//         }
//
//         stage('Package') {
//             steps {
//                 bat 'mvn package'
//             }
//         }
//
//         stage('Run App') {
//             steps {
//                 bat 'start /B java -jar target\\order_service-0.0.1-SNAPSHOT.jar'
//             }
//         }
//     }
// }
pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'JDK17'
    }

    stages {

        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/rohaney09/OrderService.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t order-service .'
            }
        }

        stage('Deploy') {
            steps {
                bat '''
                docker stop order-container || echo "no container"
                docker rm order-container || echo "no container"
                docker run -d -p 8081:8081 --name order-container order-service
                '''
            }
        }
    }
}//change

