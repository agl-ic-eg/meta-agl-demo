require agl-cluster-demo-flutter-guest.bb

SUMMARY = "AGL KVM demo guest preconfigured cluster Flutter image"

# The cluster screen is rotated in the full demo setup, so the
# default compositor configuration needs to be replaced.
IMAGE_INSTALL:remove = "weston-ini-conf-landscape"

IMAGE_INSTALL += " \
    psplash-inverted-config \
    weston-ini-conf-landscape-inverted \
"
