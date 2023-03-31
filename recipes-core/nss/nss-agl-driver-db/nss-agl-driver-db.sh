#!/bin/bash
set -e

# TLDR we need this file for chromium to connect back to kuksa.

# check if directory already exists and bail out
if test -d /home/agl-driver/.pki/nssdb ; then
    echo "Directory already exists! Doing nothing."
    exit 127
fi

# setup empty db in subfolder
mkdir -p /home/agl-driver/.pki/nssdb
certutil -N -d /home/agl-driver/.pki/nssdb --empty-password

# deploy cert into local db
certutil -A -d /home/agl-driver/.pki/nssdb -n "KuksaRootCA" -t "pC,," -i /etc/kuksa-val/CA.pem

#chown -R agl-driver:agl-driver /home/agl-driver/.pki/nssdb
