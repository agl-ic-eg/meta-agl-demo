FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
	file://0001-systemd-neard-add-multi-user.target-to-neard.service.patch \
	file://0002-ndef-avoid-dbus-property_get_type-method-on-empty-re.patch \
	"
SYSTEMD_SERVICE:${PN}:append = " neard.service"
