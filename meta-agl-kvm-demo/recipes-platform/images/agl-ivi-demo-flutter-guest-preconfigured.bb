require agl-ivi-demo-flutter-guest.bb

SUMMARY = "AGL KVM demo preconfigured guest IVI Flutter image"

# KUKSA.val always runs externally
IMAGE_FEATURES:remove = "kuksa-val-databroker"

# Everything runs on the host for now
PLATFORM_SERVICES_INSTALL = ""

# We do not want weston-terminal visible
IMAGE_INSTALL:remove = "weston-terminal-conf"
