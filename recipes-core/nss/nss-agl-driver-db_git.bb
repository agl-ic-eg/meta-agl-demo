SUMMARY = "Custom nss db hosting the kuksa certificates"
DESCRIPTION = "Custom nss db hosting the kuksa certificates for chromium"
AUTHOR = "Jan-Simon Moeller <jsmoeller@linuxfoundation.org>"
HOMEPAGE = "https://git.automotivelinux.org"
LICENSE = "MIT"
DEPENDS = " agl-session nss-native"
PV = "0.1"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    mkdir -p ${D}/home/agl-driver/.pki/nssdb
    certutil -N -d ${D}/home/agl-driver/.pki/nssdb --empty-password
}

pkg_postinst_ontarget:${PN} () {
    chown agl-driver:agl-driver -R /home/agl-driver/.pki/
}

FILES:${PN} += "/home/agl-driver/.pki/*"
