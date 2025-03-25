require agl-kvm-demo.bb

SUMMARY = "AGL KVM preconfigured Flutter demo image"

# The databroker runs on the host to simplify things when running
# clients on the host instead of just in the guests.
IMAGE_FEATURES += " \
    kuksa-val-databroker \
    kuksa-val-databroker-client \
"

# Until virtio sound is workable with QEMU, run the audio using
# services on the host for a better demo experience.  At the
# moment, this also includes the HVAC service since it does not
# make sense to try to make things more fine-grained with respect
# to configuration for where things expect to find the databroker.
# It will need to be revisited when virtio-snd, virtio-gpio, etc.
# become feasible to use.
HOST_AUDIO_INSTALL = " \
    packagegroup-agl-ivi-services-platform \
    packagegroup-agl-ivi-multimedia-platform \
    agl-service-audiomixer-systemd-databroker \
    agl-service-hvac-systemd-databroker \
    agl-service-radio-conf-kvm-demo \
    packagegroup-pipewire \
    wireplumber-config-agl \
    wireplumber-policy-config-agl \
    udisks2 \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "packagegroup-pipewire-tools mpc" , "", d)} \
"

IMAGE_INSTALL += "\
    kuksa-databroker-agl-demo-cluster \
    ${HOST_AUDIO_INSTALL} \
"

GUEST_VM1_IMAGE = "agl-ivi-demo-flutter-guest-preconfigured"
GUEST_VM2_IMAGE = "agl-cluster-demo-flutter-guest-preconfigured"
