#################################################################################################################
#
# File:        generate_caddypick_locations.rb
# Author:      David Price (david.price@swisslog.com)
# Revision:    $Revision: 29737 $
# Description: Create CaddyPickLocation, StorageLocation and Compartment entity data for data loader.
#              This can be imported directly into the relevant dataloader sheet as a csv
#
#################################################################################################################

@work_path = 1

def create_pick_locations(start_block_id, end_block_id, block_group, pick_aisle, rack_left, rack_right)
  left_walk_sequence = 1
  right_walk_sequence = 2
  
  start_block_id.upto(end_block_id) do |block_id|
    group = block_group.to_s.rjust(3, '0')
    block = block_id.to_s.rjust(4, '0')
    
    unless rack_left.nil?
      pick_location = pick_aisle.to_s + "-" + left_walk_sequence.to_s.rjust(3, '0')
      
      # Pick Left
      puts "#{pick_location}, LOC_CADDY_PICK_PICK, #{@work_path}, CADDY_PICK_WORK_ZONE, " \
        "61#{group}#{block}000, LEFT, TJM-WAREHOUSE, #{pick_location}-STORAGE, CADDY_PICK_AISLE_#{pick_aisle}"
      
      left_walk_sequence += 2
      @work_path += 1
    end
    
    unless rack_right.nil?
      pick_location = pick_aisle.to_s + "-" + right_walk_sequence.to_s.rjust(3, '0')    
      
      puts "#{pick_location}, LOC_CADDY_PICK_PICK, #{@work_path}, CADDY_PICK_WORK_ZONE, " \
        "61#{group}#{block}000, RIGHT, TJM-WAREHOUSE, #{pick_location}-STORAGE, CADDY_PICK_AISLE_#{pick_aisle}"
      
      right_walk_sequence += 2
      @work_path += 1    
    end
  end
end

def create_storage_locations(start_block_id, end_block_id, pick_aisle, pick_level, rack_left, rack_right, crane_left, crane_right, direction)
  left_walk_sequence = 1
  right_walk_sequence = 2
  x = 1
  direction == :up ? x = 1 : x = 70
  y = pick_level
  z = 1  
  
  start_block_id.upto(end_block_id) do |block_id|      
    unless rack_left.nil?
      pick_location = pick_aisle.to_s + "-" + left_walk_sequence.to_s.rjust(3, '0')
      aisle = (crane_left.to_s[-2,2]).to_i
      module_left = (crane_left.to_s[0,2]).to_i
      
      #puts "#{pick_location}-STORAGE, LOC_PALLET_STORE, #{crane_left}, CADDY_PICK_WORK_ZONE, " \
      #  "HB_AISLE#{aisle}, TJM-WAREHOUSE, #{pick_location}-COMPARTMENT, #{rack_left}, #{module_left}, #{x}, #{y}, #{z}"        
        
      sis_position = sprintf("%.2d%.3d%.3d%.2d%.2d", module_left, rack_left, x, y, z)
        
      puts "#{pick_location}-STORAGE, LOC_PALLET_STORE, #{sis_position}, CADDY_PICK_WORK_ZONE, " \
        "HB_AISLE#{aisle}, TJM-WAREHOUSE, #{pick_location}-COMPARTMENT, #{rack_left}, #{module_left}, #{x}, #{y}, #{z}"        
      
      left_walk_sequence += 2
    end
    
    unless rack_right.nil?
      pick_location = pick_aisle.to_s + "-" + right_walk_sequence.to_s.rjust(3, '0')
      aisle = (crane_right.to_s[-2,2]).to_i
      module_right = (crane_right.to_s[0,2]).to_i
      
      #puts "#{pick_location}-STORAGE, LOC_PALLET_STORE, #{crane_right}, CADDY_PICK_WORK_ZONE, " \
      #  "HB_AISLE#{aisle}, TJM-WAREHOUSE, #{pick_location}-COMPARTMENT, #{rack_right}, #{module_right}, #{x}, #{y}, #{z}" 
      
      sis_position = sprintf("%.2d%.3d%.3d%.2d%.2d", module_right, rack_right, x, y, z)
        
      puts "#{pick_location}-STORAGE, LOC_PALLET_STORE, #{sis_position}, CADDY_PICK_WORK_ZONE, " \
        "HB_AISLE#{aisle}, TJM-WAREHOUSE, #{pick_location}-COMPARTMENT, #{rack_right}, #{module_right}, #{x}, #{y}, #{z}"        
      
      right_walk_sequence += 2
    end

    direction == :up ? x += 1 : x -= 1
  end
end

def create_storage_compartments(start_block_id, end_block_id, pick_aisle, pick_level, rack_left, rack_right, crane_left, crane_right, direction)
  left_walk_sequence = 1
  right_walk_sequence = 2
  
  start_block_id.upto(end_block_id) do |block_id|
    unless rack_left.nil?
      pick_location = pick_aisle.to_s + "-" + left_walk_sequence.to_s.rjust(3, '0')
      aisle = (crane_left.to_s[-2,2]).to_i
      
      if direction == :up then
        side_of_aisle = 'RIGHT'
        aisle = (crane_left.to_s[-2,2]).to_i
      else        
        side_of_aisle = 'LEFT'
        aisle = (crane_left.to_s[-2,2]).to_i
      end                
      
      puts "#{pick_location}-COMPARTMENT, COMP_CADDY_PICK, , CADDY_PICK_WORK_ZONE, " \
        "HB_AISLE#{aisle}, TJM-WAREHOUSE, #{side_of_aisle}"        
        
      left_walk_sequence += 2
    end
    
    unless rack_right.nil?
      pick_location = pick_aisle.to_s + "-" + right_walk_sequence.to_s.rjust(3, '0')
      aisle = (crane_right.to_s[-2,2]).to_i
      
      if direction == :up then
        side_of_aisle = 'LEFT'
        aisle = (crane_right.to_s[-2,2]).to_i
      else        
        side_of_aisle = 'RIGHT'
        aisle = (crane_right.to_s[-2,2]).to_i
      end      
      puts "#{pick_location}-COMPARTMENT, COMP_CADDY_PICK, , CADDY_PICK_WORK_ZONE, " \
        "HB_AISLE#{aisle}, TJM-WAREHOUSE, #{side_of_aisle}"        
        
      right_walk_sequence += 2
    end
  end
end

if ARGV[0] == "pick_locations" then
  # Level 1 Pick Locations
  create_pick_locations(104, 173, 11, 'H1B', 40, 41)
  create_pick_locations(204, 273, 11, 'H1C', 39, 38)
  create_pick_locations(304, 373, 11, 'H1D', 36, 37)
  create_pick_locations(404, 473, 11, 'H1E', 35, 34)
  create_pick_locations(504, 573, 11, 'H1F', 32, 33)
  create_pick_locations(604, 673, 12, 'H1G', 31, 30)
  create_pick_locations(704, 773, 12, 'H1H', 28, 29)
  create_pick_locations(804, 873, 12, 'H1I', 27, 26)
  create_pick_locations(904, 973, 12, 'H1J', 24, 25)
  create_pick_locations(1004, 1073, 12, 'H1K', 23, 22)
  create_pick_locations(1104, 1173, 12, 'H1L', 20, 21)
  create_pick_locations(1204, 1273, 7, 'H1M', 19, 18)
  create_pick_locations(1304, 1373, 7, 'H1N', 16, 17)
  create_pick_locations(1404, 1473, 7, 'H1O', 15, 14)
  create_pick_locations(1504, 1573, 7, 'H1P', 12, 13)
  create_pick_locations(1604, 1673, 7, 'H1Q', 11, 10)
  create_pick_locations(1704, 1773, 7, 'H1R', 8, 9)
  create_pick_locations(1804, 1873, 7, 'H1S', 7, 6)
  create_pick_locations(1904, 1973, 7, 'H1T', 4, 5)
  create_pick_locations(2004, 2073, 7, 'H1U', 3, 2)
  create_pick_locations(2104, 2173, 7, 'H1V', nil, 1)
  
  # Level 2 Pick Locations
  create_pick_locations(3104, 3173, 31, 'H2B', 40, 41)
  create_pick_locations(3204, 3273, 31, 'H2C', 39, 38)
  create_pick_locations(3304, 3373, 31, 'H2D', 36, 37)
  create_pick_locations(3404, 3473, 31, 'H2E', 35, 34)
  create_pick_locations(3504, 3573, 31, 'H2F', 32, 33)
  create_pick_locations(3604, 3673, 32, 'H2G', 31, 30)
  create_pick_locations(3704, 3773, 32, 'H2H', 28, 29)
  create_pick_locations(3804, 3873, 32, 'H2I', 27, 26)
  create_pick_locations(3904, 3973, 32, 'H2J', 24, 25)
  create_pick_locations(4004, 4073, 32, 'H2K', 23, 22)
  create_pick_locations(4104, 4173, 32, 'H2L', 20, 21)
  create_pick_locations(4204, 4273, 27, 'H2M', 19, 18)
  create_pick_locations(4304, 4373, 27, 'H2N', 16, 17)
  create_pick_locations(4404, 4473, 27, 'H2O', 15, 14)
  create_pick_locations(4504, 4573, 27, 'H2P', 12, 13)
  create_pick_locations(4604, 4673, 27, 'H2Q', 11, 10)
  create_pick_locations(4704, 4773, 27, 'H2R', 8, 9)
  create_pick_locations(4804, 4873, 27, 'H2S', 7, 6)
  create_pick_locations(4904, 4973, 27, 'H2T', 4, 5)
  create_pick_locations(5004, 5073, 27, 'H2U', 3, 2)
  create_pick_locations(5104, 5173, 27, 'H2V', nil, 1)
end

if ARGV[0] == "storage_locations" then
  # Level 1 Storage Locations
  create_storage_locations(104, 173, 'H1B', 3, 40, 41, 3220, 3221, :up)
  create_storage_locations(204, 273, 'H1C', 3, 39, 38, 3220, 3219, :down)
  create_storage_locations(304, 373, 'H1D', 3, 36, 37, 3218, 3219, :up)
  create_storage_locations(404, 473, 'H1E', 3, 35, 34, 3218, 3217, :down)
  create_storage_locations(504, 573, 'H1F', 3, 32, 33, 3216, 3217, :up)
  create_storage_locations(604, 673, 'H1G', 3, 31, 30, 3216, 3215, :down)
  create_storage_locations(704, 773, 'H1H', 3, 28, 29, 3114, 3215, :up)
  create_storage_locations(804, 873, 'H1I', 3, 27, 26, 3114, 3113, :down)
  create_storage_locations(904, 973, 'H1J', 3, 24, 25, 3112, 3113, :up)
  create_storage_locations(1004, 1073, 'H1K', 3, 23, 22, 3112, 3111, :down)
  create_storage_locations(1104, 1173, 'H1L', 3, 20, 21, 3110, 3111, :up)
  create_storage_locations(1204, 1273, 'H1M', 3, 19, 18, 3110, 3109, :down)
  create_storage_locations(1304, 1373, 'H1N', 3, 16, 17, 3108, 3109, :up)
  create_storage_locations(1404, 1473, 'H1O', 3, 15, 14, 3108, 3007, :down)
  create_storage_locations(1504, 1573, 'H1P', 3, 12, 13, 3006, 3007, :up)
  create_storage_locations(1604, 1673, 'H1Q', 3, 11, 10, 3006, 3005, :down)
  create_storage_locations(1704, 1773, 'H1R', 3, 8, 9, 3004, 3005, :up)
  create_storage_locations(1804, 1873, 'H1S', 3, 7, 6, 3004, 3003, :down)
  create_storage_locations(1904, 1973, 'H1T', 3, 4, 5, 3002, 3003, :up)
  create_storage_locations(2004, 2073, 'H1U', 3, 3, 2, 3002, 3001, :down)
  create_storage_locations(2104, 2173, 'H1V', 3, nil, 1, nil, 3001, :up)
  
  # Level 2 Storage Locations
  create_storage_locations(3104, 3173, 'H2B', 4, 40, 41, 3220, 3221, :up)
  create_storage_locations(3204, 3273, 'H2C', 4, 39, 38, 3220, 3219, :down)
  create_storage_locations(3304, 3373, 'H2D', 4, 36, 37, 3218, 3219, :up)
  create_storage_locations(3404, 3473, 'H2E', 4, 35, 34, 3218, 3217, :down)
  create_storage_locations(3504, 3573, 'H2F', 4, 32, 33, 3216, 3217, :up)
  create_storage_locations(3604, 3673, 'H2G', 4, 31, 30, 3216, 3215, :down)
  create_storage_locations(3704, 3773, 'H2H', 4, 28, 29, 3114, 3215, :up)
  create_storage_locations(3804, 3873, 'H2I', 4, 27, 26, 3114, 3113, :down)
  create_storage_locations(3904, 3973, 'H2J', 4, 24, 25, 3112, 3113, :up)
  create_storage_locations(4004, 4073, 'H2K', 4, 23, 22, 3112, 3111, :down)
  create_storage_locations(4104, 4173, 'H2L', 4, 20, 21, 3110, 3111, :up)
  create_storage_locations(4204, 4273, 'H2M', 4, 19, 18, 3110, 3109, :down)
  create_storage_locations(4304, 4373, 'H2N', 4, 16, 17, 3108, 3109, :up)
  create_storage_locations(4404, 4473, 'H2O', 4, 15, 14, 3108, 3007, :down)
  create_storage_locations(4504, 4573, 'H2P', 4, 12, 13, 3006, 3007, :up)
  create_storage_locations(4604, 4673, 'H2Q', 4, 11, 10, 3006, 3005, :down)
  create_storage_locations(4704, 4773, 'H2R', 4, 8, 9, 3004, 3005, :up)
  create_storage_locations(4804, 4873, 'H2S', 4, 7, 6, 3004, 3003, :down)
  create_storage_locations(4904, 4973, 'H2T', 4, 4, 5, 3002, 3003, :up)
  create_storage_locations(5004, 5073, 'H2U', 4, 3, 2, 3002, 3001, :down)
  create_storage_locations(5104, 5173, 'H2V', 4, nil, 1, nil, 3001, :up)
end
  
if ARGV[0] == "compartments" then  
  # Level 1 Storage Compartments
  create_storage_compartments(104, 173, 'H1B', 3, 40, 41, 3220, 3221, :up)
  create_storage_compartments(204, 273, 'H1C', 3, 39, 38, 3220, 3219, :down)
  create_storage_compartments(304, 373, 'H1D', 3, 36, 37, 3218, 3219, :up)
  create_storage_compartments(404, 473, 'H1E', 3, 35, 34, 3218, 3217, :down)
  create_storage_compartments(504, 573, 'H1F', 3, 32, 33, 3216, 3217, :up)
  create_storage_compartments(604, 673, 'H1G', 3, 31, 30, 3216, 3215, :down)
  create_storage_compartments(704, 773, 'H1H', 3, 28, 29, 3114, 3215, :up)
  create_storage_compartments(804, 873, 'H1I', 3, 27, 26, 3114, 3113, :down)
  create_storage_compartments(904, 973, 'H1J', 3, 24, 25, 3112, 3113, :up)
  create_storage_compartments(1004, 1073, 'H1K', 3, 23, 22, 3112, 3111, :down)
  create_storage_compartments(1104, 1173, 'H1L', 3, 20, 21, 3110, 3111, :up)
  create_storage_compartments(1204, 1273, 'H1M', 3, 19, 18, 3110, 3109, :down)
  create_storage_compartments(1304, 1373, 'H1N', 3, 16, 17, 3108, 3109, :up)
  create_storage_compartments(1404, 1473, 'H1O', 3, 15, 14, 3108, 3007, :down)
  create_storage_compartments(1504, 1573, 'H1P', 3, 12, 13, 3006, 3007, :up)
  create_storage_compartments(1604, 1673, 'H1Q', 3, 11, 10, 3006, 3005, :down)
  create_storage_compartments(1704, 1773, 'H1R', 3, 8, 9, 3004, 3005, :up)
  create_storage_compartments(1804, 1873, 'H1S', 3, 7, 6, 3004, 3003, :down)
  create_storage_compartments(1904, 1973, 'H1T', 3, 4, 5, 3002, 3003, :up)
  create_storage_compartments(2004, 2073, 'H1U', 3, 3, 2, 3002, 3001, :down)
  create_storage_compartments(2104, 2173, 'H1V', 3, nil, 1, nil, 3001, :up)
  
  # Level 2 Storage Compartments
  create_storage_compartments(3104, 3173, 'H2B', 4, 40, 41, 3220, 3221, :up)
  create_storage_compartments(3204, 3273, 'H2C', 4, 39, 38, 3220, 3219, :down)
  create_storage_compartments(3304, 3373, 'H2D', 4, 36, 37, 3218, 3219, :up)
  create_storage_compartments(3404, 3473, 'H2E', 4, 35, 34, 3218, 3217, :down)
  create_storage_compartments(3504, 3573, 'H2F', 4, 32, 33, 3216, 3217, :up)
  create_storage_compartments(3604, 3673, 'H2G', 4, 31, 30, 3216, 3215, :down)
  create_storage_compartments(3704, 3773, 'H2H', 4, 28, 29, 3114, 3215, :up)
  create_storage_compartments(3804, 3873, 'H2I', 4, 27, 26, 3114, 3113, :down)
  create_storage_compartments(3904, 3973, 'H2J', 4, 24, 25, 3112, 3113, :up)
  create_storage_compartments(4004, 4073, 'H2K', 4, 23, 22, 3112, 3111, :down)
  create_storage_compartments(4104, 4173, 'H2L', 4, 20, 21, 3110, 3111, :up)
  create_storage_compartments(4204, 4273, 'H2M', 4, 19, 18, 3110, 3109, :down)
  create_storage_compartments(4304, 4373, 'H2N', 4, 16, 17, 3108, 3109, :up)
  create_storage_compartments(4404, 4473, 'H2O', 4, 15, 14, 3108, 3007, :down)
  create_storage_compartments(4504, 4573, 'H2P', 4, 12, 13, 3006, 3007, :up)
  create_storage_compartments(4604, 4673, 'H2Q', 4, 11, 10, 3006, 3005, :down)
  create_storage_compartments(4704, 4773, 'H2R', 4, 8, 9, 3004, 3005, :up)
  create_storage_compartments(4804, 4873, 'H2S', 4, 7, 6, 3004, 3003, :down)
  create_storage_compartments(4904, 4973, 'H2T', 4, 4, 5, 3002, 3003, :up)
  create_storage_compartments(5004, 5073, 'H2U', 4, 3, 2, 3002, 3001, :down)
  create_storage_compartments(5104, 5173, 'H2V', 4, nil, 1, nil, 3001, :up)
end
