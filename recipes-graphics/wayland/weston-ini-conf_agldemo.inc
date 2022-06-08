# Hopefully temporary workaround, tbtnavi should be requesting display
# on the remote output itself.
REMOTE_OUTPUT_APP_ID = "${@bb.utils.contains("AGL_FEATURES", "agl-demo-cluster-support", "tbtnavi", "", d)}"
