#################################################################################################################
#
# File:        generate_cranebox_dummy_config.rb
# Author:      David Price (david.price@swisslog.com)
# Revision:    $Revision: 29737 $
# Description: Create dummy cranebox entity data for data loader.
#              This will be provided by the commissioning engineer for the production system.
#
#################################################################################################################

def create_rack_positions(module_number, equipment, side, rack, max_x, max_y, max_z, pick_rack)
  pick_y = [11,12,13,14,19,20,21,22]
  1.upto(max_x) do |x|    
    1.upto(max_y) do |y|
      1.upto(max_z) do |z|
        sis_position = sprintf("%.2d%.3d%.3d%.2d%.2d", module_number, rack, x, y, z)
        
        flow_rack = "FALSE"
        shelf_type = 'D'
        if (pick_rack && pick_y.include?(y)) then
          shelf_type = 'R'
          flow_rack = "TRUE"
        end
        
        if ((shelf_type == 'R' and z == 1) or (shelf_type == 'D')) then
          puts "#{equipment}, #{sis_position}, 11, #{side}, #{x * 613}, #{y * 391}, #{z * 662}, 0, 0, #{rack}, #{x}, #{y}, #{z}, " \
            "#{shelf_type}, 1, #{z}, 0, TRUE, FALSE, FALSE, #{flow_rack}"
        end
      end
    end    
  end
end

if ARGV[0] == "rack_positions" then
  create_rack_positions(40, "40001", "LEFT", 1, 85, 24, 2, true)
  create_rack_positions(40, "40001", "RIGHT", 2, 85, 24, 2, false)
  create_rack_positions(40, "40002", "LEFT", 3, 85, 24, 2, false)
  create_rack_positions(40, "40002", "RIGHT", 4, 85, 24, 2, true)
  create_rack_positions(40, "40003", "LEFT", 5, 85, 24, 2, true)
  create_rack_positions(40, "40003", "RIGHT", 6, 85, 24, 2, false)
  create_rack_positions(40, "40004", "LEFT", 7, 85, 24, 2, false)
  create_rack_positions(40, "40004", "RIGHT", 8, 85, 24, 2, true)
end