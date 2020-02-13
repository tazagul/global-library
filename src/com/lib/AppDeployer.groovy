properties([[$class: 'JiraProjectProperty'], parameters([string(defaultValue: 'v1', description: 'Please provide a version number', name: 'APP_VERSION', trim: false)])])
node {
    stage("pull repo"){
        git 'https://github.com/tazagul/nodejs_app.git'
    }
    stage("Build,Tag Image"){
        sh "docker build -t app1:${APP_VERSION} ."
    }
    stage("Image Tag"){
        sh '''docker tag app1:${APP_VERSION} 111793730205.dkr.ecr.eu-west-1.amazonaws.com/app1:${APP_VERSION}'''
    }
    stage("Login to ECR"){
        sh '''$(aws ecr get-login --no-include-email --region eu-west-1)'''
    }
    stage("Push Image"){
        sh "docker images"
        sh "docker push 111793730205.dkr.ecr.eu-west-1.amazonaws.com/app1:${APP_VERSION}"
    }
    stage("Notify"){
        sh "echo Hello"
    }
}
