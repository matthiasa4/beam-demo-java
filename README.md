
Set credentials (https://cloud.google.com/docs/authentication/getting-started):
$env:GOOGLE_APPLICATION_CREDENTIALS="C:\Users\Matthias\workspace\beam-demo\Matthias Sandbox-27b375a1107b.json"


Run pipeline from Powershell:
mvn compile exec:java -D exec.mainClass=com.matthiasbaetens.gde.StreamingPipeline -D exec.args="--inputSubscription=projects/matthias-sandbox/subscriptions/frontend-log --output=out.txt" -P direct-runner