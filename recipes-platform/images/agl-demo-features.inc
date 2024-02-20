FEATURE_PACKAGES_kuksa-val-databroker-client = " \
    kuksa-certificates-agl-ca \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'packagegroup-agl-kuksa-val-databroker-devel' , '', d)} \
"
FEATURE_PACKAGES_kuksa-val-databroker = "packagegroup-agl-kuksa-val-databroker"
