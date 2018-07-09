package filters

import javax.inject.Inject
import logging.ReqIdFilter
import play.api.http.{DefaultHttpFilters, EnabledFilters}

class Filters @Inject()(
    defaultFilters: EnabledFilters,
    log: ReqIdFilter
) extends DefaultHttpFilters(defaultFilters.filters :+ log: _*)
