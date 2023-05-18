IMAGE_CONTAINER_GUEST_APPEND = " base-files-guest-fstab"
IMAGE_INSTALL:append = " \
    ${@bb.utils.contains('AGL_FEATURES', 'agl-container-guest-demo', '${IMAGE_CONTAINER_GUEST_APPEND}', '', d)} \
"

fixupfstab_for_container_guest_images() {
    rm -f  ${IMAGE_ROOTFS}/${sysconfdir}/fstab
    ln -s fstab-guest ${IMAGE_ROOTFS}/${sysconfdir}/fstab
}

rmboot_for_container_guest_images() {
    rm -rf ${IMAGE_ROOTFS}/boot
}

ROOTFS_POSTPROCESS_COMMAND += " \
    ${@bb.utils.contains('AGL_FEATURES', 'agl-container-guest-demo', 'fixupfstab_for_container_guest_images;', '', d)} \
    ${@bb.utils.contains('AGL_FEATURES', 'agl-container-guest-demo', 'rmboot_for_container_guest_images;', '', d)} \
"
