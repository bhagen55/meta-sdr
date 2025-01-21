# Borrowed from https://github.com/iot-lab/iot-lab-yocto/blob/master/meta-iotlab/recipes-support/rtl-sdr/rtl-sdr_git.bb
SUMMARY = "Software to turn the RTL2832U into a SDR"
HOMEPAGE = "http://sdr.osmocom.org/trac/wiki/rtl-sdr"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libusb1"

PV = "2.0.1+git${SRCPV}"

SRC_URI = "git://git.osmocom.org/rtl-sdr;branch=master \
          "
SRCREV = "ab2434e30da85ef19c0b90b99e8f14a7c00b8852"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF = "--enable-driver-detach"

do_install:append() {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${S}/rtl-sdr.rules ${D}${sysconfdir}/udev/rules.d
}
