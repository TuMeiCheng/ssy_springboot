#sql("findGirl")
  select * from eqp_area where areaId > ? and areaId < ?
#end


#sql("search")
  select * from eqp_area where NAME like concat('%', #para(name), '%')
#end

#sql("find")
  select * from eqp_area
  #for(x : cond)
    #(for.first ? "where": "and") #(x.key) #(x.value)
  #end
#end


