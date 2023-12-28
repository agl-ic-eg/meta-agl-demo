SUMMARY = "A small reverse proxy that can front existing gRPC servers and expose their functionality using gRPC-Web protocol, allowing for the gRPC services to be consumed from browsers."
HOMEPAGE = "https://github.com/improbable-eng/grpc-web"

inherit go-mod systemd

RDEPENDS:${PN} = "bash"
RDEPENDS:${PN}-dev = "bash"

GO_IMPORT = "github.com/improbable-eng/grpc-web"
GO_INSTALL = "${GO_IMPORT}/go/grpcwebproxy"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE.txt;md5=71a6955f3cd81a809549da266346dc59"

SRCREV = "1d9bbb09a0990bdaff0e37499570dbc7d6e58ce8"
SRC_URI = "git://${GO_IMPORT};branch=master;protocol=https \
           file://databroker-grpc-web-proxy.env \
           file://databroker-grpc-web-proxy.service"

do_compile[network] = "1"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/databroker-grpc-web-proxy.service ${D}${systemd_system_unitdir}

    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/databroker-grpc-web-proxy.env ${D}${sysconfdir}/default/databroker-grpc-web-proxy.env
}

SYSTEMD_SERVICE:${PN} = "databroker-grpc-web-proxy.service"

FILES:${PN} = "${bindir} \
               ${systemd_system_unitdir} \
               ${sysconfdir}/default "
