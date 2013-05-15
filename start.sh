#!/bin/bash

DBGOPT=
PARAM1="$1"
if [ "$PARAM1" == 'g' ]; then
  DBGOPT="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=55345"
fi

bash $RESIN_HOME/bin/resin.sh start "$DBGOPT"
