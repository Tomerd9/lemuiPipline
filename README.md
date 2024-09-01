This pipline is used as a simple example of CI/CD pipline.
in the first stage the app will be deployed in a container using docker at the CI stage, after the deployment you can run any test 
you would like on the code (instead of the sleep command add your test script/code).
In the second stage the app will be build again this time using kubernetes and exposing port 443 outside, the CD stage.
