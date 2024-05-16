require agl-ivi-demo-flutter-preconfigured.bb

SUMMARY = "AGL IVI preconfigured gateway demo Flutter image"

# We do not want a local databroker instance
IMAGE_FEATURES:remove = "kuksa-val-databroker"

FLUTTER_ICS_HOMESCREEN_CONF = "flutter-ics-homescreen-conf-gateway-demo"
ONDEMANDNAVI_CONF = "ondemandnavi-conf-gateway-demo"

IMAGE_INSTALL += " \
    agl-service-hvac-conf-gateway-demo \
    agl-service-audiomixer-conf-gateway-demo \
"