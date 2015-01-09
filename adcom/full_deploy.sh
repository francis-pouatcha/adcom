#This script is used to make a full projet deployemnt.
#Please set the $ADCOM_HOME environment variable in your profile.
#as follow
#export $ADCOM_HOME=/path/to/your/adcom

echo 'deleting deployemnt db'
cd && rm -rf addb.*
echo 'stopping jboss smoothly'
kill -9 $( ps aux | grep '[j]boss' | awk '{print $2}')
echo 'remove old deployments'
cd $JBOSS_HOME/standalone/deployments/ && rm -rf *
echo 'starting jboss'
cd $JBOSS_HOME && bin/standalone.sh &
echo 'switching to projet directory'
cd $ADCOM_HOME
echo 'cleanning the projet'
mvn clean install
echo 'cleanning deploying new artifacts'
cp adlogin.client/target/adlogin.client.war $JBOSS_HOME/standalone/deployments
cp adbase.server/target/adbase.server.war $JBOSS_HOME/standalone/deployments/
cp adres.client/target/adres.client.war $JBOSS_HOME/standalone/deployments/
cp adterm.server/target/adterm.server.war $JBOSS_HOME/standalone/deployments/
cp adbase.client/target/adbase.client.war $JBOSS_HOME/standalone/deployments/
echo 'copying the .xls file'
cp adcom.configuration/jboss-eap-6.3/adbase/data/adbase.xls $JBOSS_HOME/adbase/data/
echo 'deployement complete succeffuly, please wait until the system log the datase initialization, before starting it in the browser'
echo 'script exit.'