#!/usr/bin/env python3
# Copyright (c) 2022 Aakash Solanki, tech2aks@gmail.com
# Copyright (c) 2024 Scott Murray <scott.murray@konsulko.com>
#
# SPDX-License-Identifier: MIT

import sys
from pathlib import Path
import yaml
import asyncio
import concurrent.futures
from kuksa_client.grpc.aio import VSSClient
from kuksa_client.grpc import Datapoint
from systemd.daemon import notify

# Defaults
hostname = "localhost"
port = 55555
config_filename = "/etc/xdg/AGL/agl-vss-helper.yaml"
token_filename = "/etc/xdg/AGL/agl-vss-helper/agl-vss-helper.token"
ca_cert_filename = "/etc/kuksa-val/CA.pem"
tls_server_name = "localhost"
verbose = False

async def main():
    client = VSSClient(hostname,
                       port,
                       root_certificates=Path(ca_cert_filename),
                       tls_server_name=tls_server_name,
                       token=token,
                       ensure_startup_connection=True)
    await client.connect()
    print(f"Connected to KUKSA.val databroker at {hostname}:{port}")
    if "initialize" in config and isinstance(config["initialize"], list):
        for entry in config["initialize"]:
            if "signal" in entry and "value" in entry:
                if verbose:
                    print(f"Setting {entry['signal']} to {entry['value']}")
                await client.set_current_values({ entry["signal"] : Datapoint(entry["value"]) })

    notify("READY=1")

    if "mock" in config and isinstance(config["mock"], list):
        if len(config["mock"]) != 0:
            print(f"Mocking actuators:")
            for signal in config["mock"]:
                print(f"  {signal}")
            async for updates in client.subscribe_target_values(config["mock"]):
                for signal in updates:
                    if updates[signal] is not None:
                        if verbose:
                            print(f"Actuating {signal} to {updates[signal].value}")
                        await client.set_current_values({ signal : Datapoint(updates[signal].value) })


#
# Initialization
#

try:
    config_file = open(config_filename, "r")
    config = yaml.safe_load(config_file)
except yaml.YAMLError as exc:
    print(f"Could not parse configuration: ${exc}")
except:
    print(f"Could not read configuration")

if "verbose" in config and isinstance(config["verbose"], bool):
    verbose = config["verbose"]
if "hostname" in config and isinstance(config["hostname"], string):
    hostname = config["hostname"]
if "port" in config and isinstance(config["port"], int):
    port = config["port"]
if "use-tls" in config and isinstance(config["use-tls"], bool):
    use_tls = config["use-tls"]
if "token-file" in config and isinstance(config["token-file"], string):
    token_filename = config["token-file"]
if "ca-certificate" in config and isinstance(config["ca-certificate"], string):
    ca_cert_filename = config["ca-certificate"]

if token_filename != "":
    if verbose:
        print(f"Reading authorization token {token_filename}")
    token_file = open(token_filename, "r")
    token = token_file.read()
else:
    token = ""

print("Starting")
try:
    asyncio.run(main())
except KeyboardInterrupt:
    print("Exiting")

notify("STOPPING=1")
sys.exit(0)
