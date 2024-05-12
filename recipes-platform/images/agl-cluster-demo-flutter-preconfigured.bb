require agl-cluster-demo-flutter.bb

SUMMARY = "AGL Cluster preconfigured demo Flutter image"

# We do not want a local databroker instance
IMAGE_FEATURES:remove = "kuksa-val-databroker"

# The cluster screen is rotated in the full demo setup, so the
# default compositor configuration needs to be replaced.
IMAGE_INSTALL:remove = "weston-ini-conf-landscape"

# Cluster application configuration needs to be replaced for
# the full demo to handle different databroker configuration.
FLUTTER_CLUSTER_DASHBOARD_CONF = "flutter-cluster-dashboard-conf-demo"

IMAGE_INSTALL += " \
    psplash-inverted-config \
    weston-ini-conf-landscape-inverted \
"
