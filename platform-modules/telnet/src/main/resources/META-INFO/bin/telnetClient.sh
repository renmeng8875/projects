#!/bin/bash
domain=$1
jar=$2
port=$3
java -classpath /data/webapps/$domain/$jar:/data/webapps/$domain/$jar/lib/* com.yy.ent.platform.modules.telnet.TelnetClient $port