package arte921.spacex

const val url = "https://api.spacexdata.com/v3/launches/upcoming"
const val testmode = true

const val SUCCESSFUL = 0
const val ISLOADING = SUCCESSFUL + 1
const val HASFAILED = ISLOADING + 1
const val TESTING = HASFAILED + 1
const val JONAME = "lol"
const val FULLSAVENAME = "f"
const val JSONSAVENAME = FULLSAVENAME + "f"
const val TIMESAVENAME = JSONSAVENAME + "f"

const val FILEKEY = "yeehaw"

const val ISHEADER = 1
const val ISPROPERTY = ISHEADER+1

const val thiscantbejson = "fdsakljdflhadsjflhp8wacvbv8324cbw0123r28910389jcdnbdipe"
var iscached = false

//default setting
var showfull = false

class NamedStringIndented(val label: String, val str: String, val Indentation: Int,val typeflag: Int)