#!/bin/bash
# SPDX-License-Identifier: Apache-2.0
# (C) 2018 Jan-Simon Möller, dl9pf@gmx.de, jsmoeller@linuxfoundation.org
# (C) 2022 Scott Murray <scott.murray@konsulko.com>

#set -x
set -e

TARGET="/etc/xdg/AGL/agl-service-hvac-leds.conf"

if [ $1 ] ; then
    # The device is always 0009 -> 9 . Only change is the i2c IF .
    LED=`echo $1 | sed -e "s#0009#9#g"`
    if [ $? -eq 0 ] ; then
	echo "$LED"
	sed -e "s#@DEVICE@#$LED#" ${TARGET}.in > ${TARGET}
    else
	echo "Invalid argument"
	exit 1
    fi
else
    echo "Need argument"
    exit 1
fi
