require agl-kvm-demo-flutter-preconfigured.bb

SUMMARY = "AGL KVM + gateway preconfigured Flutter demo image"

# We only want KUKSA.val client support, not the databroker (since
# that will be running on the gateway)
IMAGE_FEATURES:remove = "kuksa-val-databroker"

# Not needed if we're not running the databroker
IMAGE_INSTALL:remove = " \
    agl-service-audiomixer-systemd-databroker \
    agl-service-hvac-systemd-databroker \
    kuksa-databroker-agl-demo-cluster \
"

IMAGE_INSTALL += "\
    agl-service-hvac-conf-gateway-demo \
    agl-service-audiomixer-conf-gateway-demo \
"

GUEST_VM1_IMAGE = "agl-ivi-demo-flutter-guest-preconfigured-gateway"
GUEST_VM2_IMAGE = "agl-cluster-demo-flutter-guest-preconfigured-gateway"
