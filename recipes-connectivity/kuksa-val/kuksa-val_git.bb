SUMMARY = "KUKSA.val, the KUKSA Vehicle Abstraction Layer"
DESCRIPTION = "KUKSA.val provides a COVESA VSS data model describing data in a vehicle."
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "EPL-2.0 & BSL-1.0 & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d9fc0efef5228704e7f5b37f27192723 \
                    file://3rd-party-libs/jsoncons/LICENSE;md5=6ee7f7ed2001e4cde4679fdb8926f820 \
                    file://3rd-party-libs/turtle/LICENSE_1_0.txt;md5=e4224ccaecb14d942c71d31bef20d78c \
                    file://3rd-party-libs/jwt-cpp/LICENSE;md5=8325a5ce4414c65ffdda392e0d96a9ff"

DEPENDS = "boost openssl mosquitto protobuf-native grpc-native grpc"

require kuksa-val.inc

SRC_URI += "file://kuksa-val.service \
            file://0001-Make-Boost-requirements-more-liberal.patch \
            file://0002-Fix-gRPC-configuration-for-OE-cross-compiling.patch \
            file://0003-Make-install-locations-configurable.patch \
            file://0004-Disable-default-fetch-and-build-of-googletest.patch \
"

inherit cmake pkgconfig systemd useradd

SYSTEMD_SERVICE:${PN} = "kuksa-val.service"

USERADD_PACKAGES = "${PN}"
USERADDEXTENSION = "useradd-staticids"
GROUPADD_PARAM:${PN} = "-g 900 kuksa ;"
USERADD_PARAM:${PN} = "--system -g 900 -u 900 -o -d / --shell /bin/nologin kuksa ;"

# Configure file locations more along the lines of FHS instead of kuksa.val's
# default locations.
EXTRA_OECMAKE = " \
    -DKUKSA_INSTALL_BINDIR=${bindir} \
    -DKUKSA_INSTALL_CERTDIR=${sysconfdir}/kuksa-val \
    -DKUKSA_INSTALL_DATADIR=${datadir}/kuksa-val \
    -DKUKSA_INSTALL_CONFIGDIR=${sysconfdir}/kuksa-val \
"

do_install:append() {
    # Lower the logging level used in the installed config.ini from the upstream
    # default of "ALL", which seems to cause performance issues at the moment.
    sed -i 's/^log-level = .*/log-level = WARNING/' ${D}/${sysconfdir}/kuksa-val/config.ini

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-val.service ${D}${systemd_system_unitdir}
    fi

    # Restrict server certificate access
    # NOTE: The client certificates are left alone here for client
    #       development convenience for now, but this will need to
    #       be revisited.
    chmod 640 ${D}${sysconfdir}/kuksa-val/Server.key
    chgrp 900 ${D}${sysconfdir}/kuksa-val/Server.key
    chmod 640 ${D}${sysconfdir}/kuksa-val/Server.pem
    chgrp 900 ${D}${sysconfdir}/kuksa-val/Server.pem
}

FILES:${PN} += "${systemd_system_unitdir} ${datadir}"

