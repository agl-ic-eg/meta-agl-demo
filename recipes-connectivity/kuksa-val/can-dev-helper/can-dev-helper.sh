#!/bin/bash

#
# Script to bring up CAN interface configured for the kuksa.val
# CAN feeder in /etc/kuksa-dbc-feeder/dbc_feeder.ini as vcan
# interfaces if no physical interface is present.
#

vcan_up() {
    if [ -n "$1" ]; then
        echo "Bringing up $1 as virtual CAN device"
        ip link add dev $1 type vcan
        ip link set up $1
    fi
}

CONF=/etc/kuksa-dbc-feeder/config.ini
if [ $# -gt 0 ]; then
    CONF=$1
fi

if [ ! -f $CONF ]; then
    exit 0
fi

# Ideally the parsing would take the "[can]" section into
# account, but this should work for now.
interface=$(sed -nr '/\[can\]/,/\[/{s/^port *= *//p}' $CONF)
if [ -n "$interface" ]; then
    echo "Checking $interface"
    if ! ifconfig $interface >/dev/null 2>&1; then
        vcan_up $interface
    fi
fi

exit 0
