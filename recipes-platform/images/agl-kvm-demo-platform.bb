DESCRIPTION = "AGL KVM+QEMU Demo Platform image."

LICENSE = "MIT"

require recipes-platform/images/agl-image-weston.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

# Add packages for KVM+QEMU demo platform here
IMAGE_INSTALL:append = " \
    packagegroup-agl-image-minimal \
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

# Potential size reduction options
#IMAGE_LINGUAS = " "
#NO_RECOMMENDATIONS = "1"

GUEST_MACHINE ?= "virtio-${TUNE_ARCH}"

GUEST_VM1_IMAGE ?= "agl-ivi-demo-platform-flutter"
GUEST_VM2_IMAGE ?= "agl-cluster-demo-platform-flutter"

GUEST_IMAGES ?= "agl-kvm-guest:${GUEST_VM1_IMAGE} agl-kvm-guest:${GUEST_VM2_IMAGE}"

QEMU_GUEST_CONFIGS ?= ""

# Handle modification of IMAGE_LINK_NAME done by ULCB builds with Kingfisher support
MACHINE_SUFFIX = "${@bb.utils.contains('AGL_FEATURES', 'kingfisher', '-kf', '', d)}"

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
        src="${TOPDIR}/tmp-${config}/deploy/images/${GUEST_MACHINE}/${image}-${GUEST_MACHINE}${MACHINE_SUFFIX}.ext4"
        bbnote "Installing ${src}"
        install -m 0600 ${src} ${IMAGE_ROOTFS}/var/lib/machines/${name}/
	# Placeholder until booting from kernel in VM image is worked out
        install -m 0600 ${TOPDIR}/tmp-${config}/deploy/images/${GUEST_MACHINE}/Image-${GUEST_MACHINE}.bin ${IMAGE_ROOTFS}/var/lib/machines/${name}/
    done
}

ROOTFS_POSTPROCESS_COMMAND += "install_guest_images; "

IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
