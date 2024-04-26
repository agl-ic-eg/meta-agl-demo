SUMMARY = "AGL KVM+QEMU Demo Platform image."
LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb
require agl-demo-features.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

# If building with "agl-kvm-host-kuksa", the databroker and likely
# some clients run on the host
IMAGE_FEATURES += " \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "kuksa-val-databroker kuksa-val-databroker-client", "", d)} \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "can-test-tools" , "", d)} \
"

# Add packages for KVM+QEMU demo platform here
IMAGE_INSTALL += " \
    packagegroup-agl-core-connectivity \
    kernel-image \
    agl-compositor \
    weston-ini-conf-kvm \
    output-udev-conf \
    native-shell-client \
    qemu \
    ${QEMU_GUEST_CONFIGS} \
    util-linux-taskset \
    screen \
    simple-can-simulator \
    alsa-utils \
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
    agl-service-radio-conf-kvm-demo \
    packagegroup-pipewire \
    wireplumber-config-agl \
    wireplumber-policy-config-agl \
    mpd \
    udisks2 \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "packagegroup-pipewire-tools mpc" , "", d)} \
"

IMAGE_INSTALL += "\
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-kuksa", "kuksa-databroker-agl-demo-cluster", "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-audio", "${HOST_AUDIO_INSTALL}", "", d)} \
"

# Potential size reduction options
#IMAGE_LINGUAS = " "
#NO_RECOMMENDATIONS = "1"

GUEST_MACHINE ?= "virtio-${TUNE_ARCH}"

GUEST_VM1_IMAGE ?= "agl-ivi-demo-flutter"
GUEST_VM2_IMAGE ?= "agl-cluster-demo-flutter"

GUEST_IMAGES ?= "agl-kvm-guest:${GUEST_VM1_IMAGE} agl-kvm-guest:${GUEST_VM2_IMAGE}"

QEMU_GUEST_CONFIGS ?= ""

python __anonymous() {
    for c in (d.getVar('GUEST_IMAGES') or "").split():
        (mc, image) = c.split(':')
        dependency = 'mc::' + mc + ':' + image + ':do_image_complete'
        d.appendVarFlag('do_rootfs', 'mcdepends', ' ' + dependency)

        # Assume there is a qemu-config-X package for guest image X
        d.appendVar('QEMU_GUEST_CONFIGS', ' ' + 'qemu-config-' + image)
}

install_guest_images() {
    for c in ${GUEST_IMAGES}; do
        config=${c%:*}
        image=${c#*:}
        name=${image}
        rm -rf  ${IMAGE_ROOTFS}/var/lib/machines/${name}
        install -m 0755 -d ${IMAGE_ROOTFS}/var/lib/machines/${name}
        src="${TOPDIR}/tmp-${config}/deploy/images/${GUEST_MACHINE}/${image}-${GUEST_MACHINE}.ext4"
        bbnote "Installing ${src}"
        install -m 0600 ${src} ${IMAGE_ROOTFS}/var/lib/machines/${name}/
	# Placeholder until booting from kernel in VM image is worked out
        install -m 0600 ${TOPDIR}/tmp-${config}/deploy/images/${GUEST_MACHINE}/Image-${GUEST_MACHINE}.bin ${IMAGE_ROOTFS}/var/lib/machines/${name}/
    done
}

ROOTFS_POSTPROCESS_COMMAND += "install_guest_images; "

IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
