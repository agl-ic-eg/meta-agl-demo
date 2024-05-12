require recipes-platform/images/agl-cluster-demo-flutter.bb

SUMMARY = "AGL KVM demo guest cluster Flutter image"

# We do not want a local databroker instance
IMAGE_FEATURES:remove = "kuksa-val-databroker"

FLUTTER_CLUSTER_DASHBOARD_CONF = "flutter-cluster-dashboard-conf-kvm-demo"
