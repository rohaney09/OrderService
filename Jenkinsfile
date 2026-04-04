pipeline {
    agent any

    tools {
        maven 'Maven'   // make sure Maven is configured in Jenkins
        jdk 'JDK17'     // match your Java version
    }

    stages {

        stage('Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/rohaney09/OrderService.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }

        stage('Run App') {
            steps {
                bat 'nohup java -jar target/*.jar &'
            }
        }
    }
}
