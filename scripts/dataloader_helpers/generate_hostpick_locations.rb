#################################################################################################################
#
# File:        generate_hostpick_locations.rb
# Author:      David Price (david.price@swisslog.com)
# Revision:    $Revision: 29737 $
# Description: Create HostPickLocation, StorageLocation and Compartment entity data for data loader.
#              This can be imported directly into the relevant dataloader sheet as a csv
#
#################################################################################################################

@work_path = 1

def create_pick_locations(start_pick_id, end_pick_id, pick_zone, pick_level)
  start_pick_id.upto(end_pick_id) do |pick_id|    
    pick_location = pick_zone + pick_id.to_s + pick_level
    
    puts "#{pick_location},LOC_HOST_PICK_PICK,#{@work_path},WZ_HOST_PICK," \
      "TJM-WAREHOUSE,#{pick_location}-STORAGE,"
    
    @work_path += 1
  end
end

def create_storage_locations(start_pick_id, end_pick_id, pick_zone, pick_level, module_number, aisle, rack, reverse_x, y, z)
  start_pick_id.upto(end_pick_id) do |pick_id|    
    pick_location = pick_zone + pick_id.to_s + pick_level
    
    x = reverse_x ? ((end_pick_id - pick_id) + 1) : pick_id
    
    sis_position = sprintf("%.2d%.3d%.3d%.2d%.2d", module_number, rack, x, y, z)
        
    puts "#{pick_location}-STORAGE,LOC_MINILOAD_STORE,#{sis_position},WZ_HOST_PICK," \
      "ML_AISLE#{aisle},TJM-WAREHOUSE,#{pick_location}-COMPARTMENT,#{rack},#{module_number},#{x},#{y},#{z}"
        
  end
end

def create_storage_compartments(start_pick_id, end_pick_id, pick_zone, pick_level, aisle, side_of_aisle)
  start_pick_id.upto(end_pick_id) do |pick_id|    
    pick_location = pick_zone + pick_id.to_s + pick_level
    
    puts "#{pick_location}-COMPARTMENT,COMP_HOST_PICK,,WZ_HOST_PICK," \
      "ML_AISLE#{aisle},TJM-WAREHOUSE,#{side_of_aisle}"
  end
end

if ARGV[0] == "pick_locations" then
  # Level 1 Pick Locations
  create_pick_locations(1, 85, 'A', 'A')
  create_pick_locations(1, 85, 'A', 'B')
  create_pick_locations(1, 85, 'A', 'C')
  create_pick_locations(1, 85, 'A', 'D')
  create_pick_locations(1, 85, 'B', 'A')
  create_pick_locations(1, 85, 'B', 'B')
  create_pick_locations(1, 85, 'B', 'C')
  create_pick_locations(1, 85, 'B', 'D')
  create_pick_locations(1, 85, 'C', 'A')
  create_pick_locations(1, 85, 'C', 'B')
  create_pick_locations(1, 85, 'C', 'C')
  create_pick_locations(1, 85, 'C', 'D')
  create_pick_locations(1, 85, 'D', 'A')
  create_pick_locations(1, 85, 'D', 'B')
  create_pick_locations(1, 85, 'D', 'C')
  create_pick_locations(1, 85, 'D', 'D')
  
  # Level 2 Pick Locations
  create_pick_locations(1, 85, 'W', 'A')
  create_pick_locations(1, 85, 'W', 'B')
  create_pick_locations(1, 85, 'W', 'C')
  create_pick_locations(1, 85, 'W', 'D')
  create_pick_locations(1, 85, 'X', 'A')
  create_pick_locations(1, 85, 'X', 'B')
  create_pick_locations(1, 85, 'X', 'C')
  create_pick_locations(1, 85, 'X', 'D')
  create_pick_locations(1, 85, 'Y', 'A')
  create_pick_locations(1, 85, 'Y', 'B')
  create_pick_locations(1, 85, 'Y', 'C')
  create_pick_locations(1, 85, 'Y', 'D')
  create_pick_locations(1, 85, 'Z', 'A')
  create_pick_locations(1, 85, 'Z', 'B')
  create_pick_locations(1, 85, 'Z', 'C')
  create_pick_locations(1, 85, 'Z', 'D')
end

if ARGV[0] == "storage_locations" then
  # Level 1 Storage Locations
  create_storage_locations(1, 85, 'A', 'A', 40, 1, 1, true, 11, 1)
  create_storage_locations(1, 85, 'A', 'B', 40, 1, 1, true, 12, 1)
  create_storage_locations(1, 85, 'A', 'C', 40, 1, 1, true, 13, 1)
  create_storage_locations(1, 85, 'A', 'D', 40, 1, 1, true, 14, 1)
  create_storage_locations(1, 85, 'B', 'A', 40, 2, 4, false, 11, 1)
  create_storage_locations(1, 85, 'B', 'B', 40, 2, 4, false, 12, 1)
  create_storage_locations(1, 85, 'B', 'C', 40, 2, 4, false, 13, 1)
  create_storage_locations(1, 85, 'B', 'D', 40, 2, 4, false, 14, 1)
  create_storage_locations(1, 85, 'C', 'A', 40, 3, 5, true, 11, 1)
  create_storage_locations(1, 85, 'C', 'B', 40, 3, 5, true, 12, 1)
  create_storage_locations(1, 85, 'C', 'C', 40, 3, 5, true, 13, 1)
  create_storage_locations(1, 85, 'C', 'D', 40, 3, 5, true, 14, 1)
  create_storage_locations(1, 85, 'D', 'A', 40, 4, 8, false, 11, 1)
  create_storage_locations(1, 85, 'D', 'B', 40, 4, 8, false, 12, 1)
  create_storage_locations(1, 85, 'D', 'C', 40, 4, 8, false, 13, 1)
  create_storage_locations(1, 85, 'D', 'D', 40, 4, 8, false, 14, 1)
  
  # Level 2 Storage Locations
  create_storage_locations(1, 85, 'W', 'A', 40, 1, 1, true, 19, 1)
  create_storage_locations(1, 85, 'W', 'B', 40, 1, 1, true, 20, 1)
  create_storage_locations(1, 85, 'W', 'C', 40, 1, 1, true, 21, 1)
  create_storage_locations(1, 85, 'W', 'D', 40, 1, 1, true, 22, 1)
  create_storage_locations(1, 85, 'X', 'A', 40, 2, 4, false, 19, 1)
  create_storage_locations(1, 85, 'X', 'B', 40, 2, 4, false, 20, 1)
  create_storage_locations(1, 85, 'X', 'C', 40, 2, 4, false, 21, 1)
  create_storage_locations(1, 85, 'X', 'D', 40, 2, 4, false, 22, 1)
  create_storage_locations(1, 85, 'Y', 'A', 40, 3, 5, true, 19, 1)
  create_storage_locations(1, 85, 'Y', 'B', 40, 3, 5, true, 20, 1)
  create_storage_locations(1, 85, 'Y', 'C', 40, 3, 5, true, 21, 1)
  create_storage_locations(1, 85, 'Y', 'D', 40, 3, 5, true, 22, 1)
  create_storage_locations(1, 85, 'Z', 'A', 40, 4, 8, false, 19, 1)
  create_storage_locations(1, 85, 'Z', 'B', 40, 4, 8, false, 20, 1)
  create_storage_locations(1, 85, 'Z', 'C', 40, 4, 8, false, 21, 1)
  create_storage_locations(1, 85, 'Z', 'D', 40, 4, 8, false, 22, 1)
end

if ARGV[0] == "compartments" then
  # Level 1 Compartments
  create_storage_compartments(1, 85, 'A', 'A', 1, 'LEFT')
  create_storage_compartments(1, 85, 'A', 'B', 1, 'LEFT')
  create_storage_compartments(1, 85, 'A', 'C', 1, 'LEFT')
  create_storage_compartments(1, 85, 'A', 'D', 1, 'LEFT')
  create_storage_compartments(1, 85, 'B', 'A', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'B', 'B', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'B', 'C', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'B', 'D', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'C', 'A', 3, 'LEFT')
  create_storage_compartments(1, 85, 'C', 'B', 3, 'LEFT')
  create_storage_compartments(1, 85, 'C', 'C', 3, 'LEFT')
  create_storage_compartments(1, 85, 'C', 'D', 3, 'LEFT')
  create_storage_compartments(1, 85, 'D', 'A', 4, 'RIGHT')
  create_storage_compartments(1, 85, 'D', 'B', 4, 'RIGHT')
  create_storage_compartments(1, 85, 'D', 'C', 4, 'RIGHT')
  create_storage_compartments(1, 85, 'D', 'D', 4, 'RIGHT')
  
  # Level 2 Compartments
  create_storage_compartments(1, 85, 'W', 'A', 1, 'LEFT')
  create_storage_compartments(1, 85, 'W', 'B', 1, 'LEFT')
  create_storage_compartments(1, 85, 'W', 'C', 1, 'LEFT')
  create_storage_compartments(1, 85, 'W', 'D', 1, 'LEFT')
  create_storage_compartments(1, 85, 'X', 'A', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'X', 'B', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'X', 'C', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'X', 'D', 2, 'RIGHT')
  create_storage_compartments(1, 85, 'Y', 'A', 3, 'LEFT')
  create_storage_compartments(1, 85, 'Y', 'B', 3, 'LEFT')
  create_storage_compartments(1, 85, 'Y', 'C', 3, 'LEFT')
  create_storage_compartments(1, 85, 'Y', 'D', 3, 'LEFT')
  create_storage_compartments(1, 85, 'Z', 'A', 4, 'RIGHT')
  create_storage_compartments(1, 85, 'Z', 'B', 4, 'RIGHT')
  create_storage_compartments(1, 85, 'Z', 'C', 4, 'RIGHT')
  create_storage_compartments(1, 85, 'Z', 'D', 4, 'RIGHT')
end