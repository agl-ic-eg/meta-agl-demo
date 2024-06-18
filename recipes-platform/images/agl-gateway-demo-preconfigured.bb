require agl-gateway-demo.bb

SUMMARY = "AGL gateway preconfigured demo image"

# Second CAN interface will be connected to the demo setup
# steering wheel & HVAC in the full demo.
IMAGE_INSTALL += " \
    kuksa-can-provider-conf-gw-hardware \
    vss-agl-gw-hardware \
"
