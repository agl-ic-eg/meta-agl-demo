require agl-ivi-demo-qt.bb

SUMMARY = "AGL IVI preconfigured demo Qt image"

IMAGE_FEATURES += "agl-demo-cluster-support"

# We do not want weston-terminal visible
IMAGE_INSTALL:remove = "weston-terminal-conf"

IMAGE_INSTALL += " \
    weston-ini-conf-remoting \
    demo-i2c-udev-conf \
    simple-can-simulator \
"