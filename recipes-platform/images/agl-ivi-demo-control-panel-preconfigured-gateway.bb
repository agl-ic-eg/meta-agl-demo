require agl-ivi-demo-control-panel.bb

SUMMARY = "AGL gateway demo control panel image"

IMAGE_FEATURES += "kuksa-val-databroker"

IMAGE_INSTALL += " \
    agl-demo-control-panel-conf-gateway-demo \
    vss-agl-control-panel \
    kuksa-can-provider-conf-control-panel \
"
