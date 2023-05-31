DESCRIPTION = "Build MOST driver"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://mostcore/COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

inherit module

PV = "0.1"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/most;protocol=https;branch=${AGL_BRANCH} \
        file://0002-src-most-add-auto-conf-feature.patch \
        file://0003-core-remove-kernel-log-for-MBO-status.patch \
        file://0004-most-video-set-device_caps.patch \
        file://0005-most-video-set-V4L2_CAP_DEVICE_CAPS-flag.patch \
        file://0006-dim2-fix-startup-sequence.patch \
        file://0007-dim2-use-device-tree.patch \
        file://0008-dim2-read-clock-speed-from-the-device-tree.patch \
        file://0009-dim2-use-device-for-coherent-memory-allocation.patch \
        file://0010-backport-usb-setup-timer.patch \
        file://0011-handle-snd_pcm_lib_mmap_vmalloc-removal.patch \
        file://0012-Fix-build-with-5.4-kernel.patch \
        file://0013-Fix-build-with-5.7-kernel.patch \
        file://0014-Fix-build-with-5.9-kernel.patch \
        file://0001-Fix-LINUX_VERSION_CODE-check-for-dim2_hdm.patch \
        "

S = "${WORKDIR}/git/driver"
SRCREV = "e4dbbaf9e7652efaed0df3e0aab4464f5f228573"

KERNEL_MODULE_AUTOLOAD += "aim_cdev aim_sound aim_network aim_v4l2 hdm_i2c hdm_dim2 hdm_usb mostcore"
